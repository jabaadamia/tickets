"use client";

import React, { useEffect, useState } from "react";
import { getMyTickets } from "@/lib/api/tickets";
import { TicketResponse } from "@/types";
import TicketCard from "@/components/tickets/TicketCard";

export default function MyTicketsPage() {
  const [tickets, setTickets] = useState<TicketResponse[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    let isMounted = true;

    getMyTickets()
      .then((data) => {
        if (isMounted) setTickets(data);
      })
      .catch((err) => {
        console.error("Failed to fetch tickets:", err);
        if (isMounted) setError("Could not load your tickets.");
      })
      .finally(() => {
        if (isMounted) setIsLoading(false);
      });

    return () => {
      isMounted = false;
    };
  }, []);

  return (
    <div className="max-w-3xl mx-auto p-4">
      <h1 className="text-2xl font-bold mb-6">My Tickets</h1>

      {isLoading && <p className="text-gray-500">Loading tickets...</p>}
      {error && <p className="text-red-500">{error}</p>}

      {!isLoading && !error && tickets.length === 0 && (
        <p className="text-gray-500">
          You don't have any tickets yet. Confirm an order to get tickets.
        </p>
      )}

      <div className="space-y-3">
        {tickets.map((ticket) => (
          <TicketCard key={ticket.id} ticket={ticket} />
        ))}
      </div>
    </div>
  );
}