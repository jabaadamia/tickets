"use client";

import React, { useState } from "react";
import { useRouter } from "next/navigation";
import { TicketTypeResponse, CreateOrderItemRequest } from "@/types";
import { createDraftOrder } from "@/lib/api/orders";

interface TicketTypeSelectorProps {
  ticketTypes: TicketTypeResponse[];
  eventId: number;
}

export default function TicketTypeSelector({
  ticketTypes,
  eventId,
}: TicketTypeSelectorProps) {
  const router = useRouter();
  const [quantities, setQuantities] = useState<Record<number, number>>({});
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [error, setError] = useState<string | null>(null);

  if (ticketTypes.length === 0) {
    return <p className="text-gray-500 mt-6">Tickets not available yet.</p>;
  }

  const handleQuantityChange = (ticketTypeId: number, value: number, max: number) => {
    const clamped = Math.max(0, Math.min(value, max));
    setQuantities(prev => ({ ...prev, [ticketTypeId]: clamped }));
  };

  const items: CreateOrderItemRequest[] = ticketTypes
    .map(tt => ({ ticketTypeId: tt.id, quantity: quantities[tt.id] ?? 0 }))
    .filter(item => item.quantity > 0);

  const total = ticketTypes.reduce((sum, tt) => {
    const qty = quantities[tt.id] ?? 0;
    return sum + qty * tt.price;
  }, 0);

  const handleBuy = async () => {
    if (items.length === 0) return;
    setIsSubmitting(true);
    setError(null);
    try {
      const order = await createDraftOrder({ items });
      router.push(`/orders/${order.id}`);
    } catch (err) {
      console.error("Failed to create order:", err);
      setError("Could not create order. Please try again.");
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className="mt-8 border-t pt-6">
      <h2 className="text-xl font-bold mb-4">Tickets</h2>
      <div className="space-y-4">
        {ticketTypes.map(tt => {
          const available = tt.availableQuantity;
          const qty = quantities[tt.id] ?? 0;
          const soldOut = available <= 0;

          return (
            <div
              key={tt.id}
              className="flex items-center justify-between border rounded p-4"
            >
              <div>
                <p className="font-semibold">{tt.name}</p>
                <p className="text-sm text-gray-600">{tt.description}</p>
                <p className="text-sm text-gray-800 mt-1">{tt.price} gel</p>
                {soldOut && (
                  <p className="text-sm text-red-500 mt-1">Sold out</p>
                )}
              </div>

              {!soldOut && (
                <input
                  type="number"
                  min={0}
                  max={Math.min(available, tt.maxPurchase)}
                  value={qty}
                  onChange={e =>
                    handleQuantityChange(
                      tt.id,
                      Number(e.target.value),
                      Math.min(available, tt.maxPurchase)
                    )
                  }
                  className="w-20 border rounded px-2 py-1 text-center"
                />
              )}
            </div>
          );
        })}
      </div>

      {error && <p className="text-red-500 mt-3">{error}</p>}

      <div className="flex items-center justify-between mt-6">
        <p className="font-semibold text-lg">Total: {total} gel</p>
        <button
          onClick={handleBuy}
          disabled={items.length === 0 || isSubmitting}
          className="bg-blue-500 text-white px-6 py-2 rounded hover:bg-blue-600 disabled:bg-gray-300 disabled:cursor-not-allowed"
        >
          {isSubmitting ? "Processing..." : "Buy Tickets"}
        </button>
      </div>
    </div>
  );
}