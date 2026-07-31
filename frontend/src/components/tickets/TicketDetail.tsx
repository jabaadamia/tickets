import React from "react";
import { TicketResponse } from "@/types";
import { formatDate } from "@/lib/formatters";

interface TicketDetailProps {
  ticket: TicketResponse;
}

function extractOrderNumber(ticketNumber: string): string | null {
  const match = ticketNumber.match(/^TKT-(ORD-[A-Z0-9]+)-/);
  return match ? match[1] : null;
}

export default function TicketDetail({ ticket }: TicketDetailProps) {
  const orderNumber = extractOrderNumber(ticket.ticketNumber);
  const qrImageUrl = `https://api.qrserver.com/v1/create-qr-code/?size=260x260&data=${encodeURIComponent(
    ticket.qrCode
  )}`;

  return (
    <div className="max-w-md mx-auto p-4">
      <div className="border rounded p-6 text-center">
        <span
          className={`inline-block px-3 py-1 rounded text-xs font-semibold mb-4 ${
            ticket.status === "VALID"
              ? "bg-green-100 text-green-700"
              : "bg-gray-200 text-gray-600"
          }`}
        >
          {ticket.status}
        </span>

        <h1 className="text-xl font-bold mb-1">{ticket.ticketTypeName}</h1>
        <p className="text-gray-500 text-sm mb-4">#{ticket.ticketNumber}</p>

        {/* eslint-disable-next-line @next/next/no-img-element */}
        <img
          src={qrImageUrl}
          alt="Ticket QR code"
          width={260}
          height={260}
          className="mx-auto mb-4"
        />

        <p className="text-sm text-gray-600">
          Present this QR code at the entrance for check-in.
        </p>

        <div className="text-left mt-6 space-y-2 text-sm border-t pt-4">
          {orderNumber && (
            <div className="flex justify-between">
              <span className="text-gray-500">Order</span>
              <span className="font-medium">{orderNumber}</span>
            </div>
          )}
          {ticket.seatInfo && (
            <div className="flex justify-between">
              <span className="text-gray-500">Seat</span>
              <span className="font-medium">{ticket.seatInfo}</span>
            </div>
          )}
          <div className="flex justify-between">
            <span className="text-gray-500">Email</span>
            <span className="font-medium">{ticket.email}</span>
          </div>
          {ticket.checkedInAt && (
            <div className="flex justify-between">
              <span className="text-gray-500">Checked in</span>
              <span className="font-medium">
                {formatDate(ticket.checkedInAt)}
              </span>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}