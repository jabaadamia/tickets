"use client";

import React, { useEffect, useState } from "react";
import { useParams } from "next/navigation";
import { getMyTicketById } from "@/lib/api/tickets";
import { TicketResponse } from "@/types";
import TicketDetail from "@/components/tickets/TicketDetail";

export default function TicketPage() {
  const params = useParams();
  const id = Number(params.id);

  const [ticket, setTicket] = useState<TicketResponse | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [notFound, setNotFound] = useState(false);

  useEffect(() => {
    let isMounted = true;

    getMyTicketById(id)
      .then((data) => {
        if (isMounted) setTicket(data);
      })
      .catch((err) => {
        console.error("Failed to fetch ticket:", err);
        if (isMounted) setNotFound(true);
      })
      .finally(() => {
        if (isMounted) setIsLoading(false);
      });

    return () => {
      isMounted = false;
    };
  }, [id]);

  if (isLoading) {
    return <p className="text-center mt-8 text-gray-500">Loading ticket...</p>;
  }

  if (notFound || !ticket) {
    return <p className="text-center mt-8 text-red-500">Ticket not found</p>;
  }

  return <TicketDetail ticket={ticket} />;
}