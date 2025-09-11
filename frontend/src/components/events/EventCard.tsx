import React from "react";
import { formatDate } from "@/lib/formatters";
import { Event } from "@/lib/events";

interface EventCardProps {
  event: Event;
};

export default function EventCard({ event }: EventCardProps) {
  const imageUrl = event.thumbnailUrl
    ? `${process.env.IMAGE_BASE_URL}${event.thumbnailUrl}`
    : undefined;

  return (
    <div className="rounded-lg p-4 flex flex-col">
      <a
        href={`/events/${event.id}`}
        className="mt-2 text-blue-500"
      >
        {event.thumbnailUrl && (
          <img
            src={imageUrl}
            alt={event.title}
            className="rounded mb-2 w-80 h-40 object-cover"
          />
        )}
        <h2 className="font-bold text-lg text-gray-600">{event.title}</h2>
        <p className="text-sm text-gray-600">
          {formatDate(event.date)}
        </p>
        <p className="text-sm text-blue-500">{event.location.name}</p>
      </a>
    </div>
  );
}
