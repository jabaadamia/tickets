import React from "react";
import Link from "next/link";
import { TicketResponse } from "@/types";

interface TicketCardProps {
  ticket: TicketResponse;
}

// Order number isn't returned directly on TicketResponse — parsed out of
// ticketNumber ("TKT-{orderNumber}-{ticketTypeId}-{suffix}") for display only.
// Not a reliable link target — just a readable label.
function extractOrderNumber(ticketNumber: string): string | null {
  const match = ticketNumber.match(/^TKT-(ORD-[A-Z0-9]+)-/);
  return match ? match[1] : null;
}

export default function TicketCard({ ticket }: TicketCardProps) {
  const orderNumber = extractOrderNumber(ticket.ticketNumber);

  return (
    <Link
      href={`/tickets/${ticket.id}`}
      className="block border rounded p-4 hover:bg-gray-50 transition"
    >
      <div className="flex justify-between items-center">
        <div>
          <p className="font-semibold">{ticket.ticketTypeName}</p>
          <p className="text-sm text-gray-500">#{ticket.ticketNumber}</p>
          {orderNumber && (
            <p className="text-xs text-gray-400">Order: {orderNumber}</p>
          )}
        </div>
        <span
          className={`px-3 py-1 rounded text-xs font-semibold ${
            ticket.status === "VALID"
              ? "bg-green-100 text-green-700"
              : "bg-gray-200 text-gray-600"
          }`}
        >
          {ticket.status}
        </span>
      </div>
    </Link>
  );
}