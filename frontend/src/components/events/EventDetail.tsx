import React from "react";
import Image from "next/image";
import { Event, TicketTypeResponse } from "@/types";
import { formatDate } from "@/lib/formatters";
import Category from "@/components/Category";
import TicketTypeSelector from "@/components/orders/TicketTypeSelector";

interface EventDetailProps {
  event: Event;
  ticketTypes: TicketTypeResponse[];
}

export default function EventDetail({ event, ticketTypes }: EventDetailProps) {
  const imageUrl = event.thumbnailUrl
    ? `${process.env.NEXT_PUBLIC_API_URL?.replace(/\/api$/, "")}${event.thumbnailUrl}`
    : undefined;

  const locationUrl = `https://www.google.com/maps/search/?api=1&query=${event.location.latitude},${event.location.longitude}`;

  return (
    <div className="max-w-4/5 mx-auto p-4">
      {imageUrl && (
        <Image
          src={imageUrl}
          alt={event.title}
          width={1200}
          height={400}
          unoptimized
          className="w-full max-h-64 object-cover rounded mb-4"
        />
      )}
      <div className="mb-4 flex justify-between">
        <div className="w-4/5 mr-6">
          <h1 className="text-3xl font-bold mb-2">{event.title}</h1>
          <p className="mb-4">{event.description}</p>
          <div className="mt-2">
            {event.categories.map(c => (
              <Category key={c.name} category={c.name} />
            ))}
          </div>
        </div>

        <div className="w-1/5 text-right flex flex-col items-center">
          <p className="text-gray-600 mb-2">{formatDate(event.date)}</p>
          <a href={locationUrl} target="_blank" rel="noreferrer">
            <p className="inline text-blue-500">
              {event.location.name}, {event.location.address}, {event.location.city}
            </p>
          </a>
        </div>
      </div>

      <TicketTypeSelector ticketTypes={ticketTypes} eventId={event.id} />
    </div>
  );
}