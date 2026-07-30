"use client";

import React, { useState } from "react";
import { useRouter } from "next/navigation";
import { OrderResponse } from "@/types";
import { confirmOrder, cancelOrder } from "@/lib/api/orders";
import { formatDate } from "@/lib/formatters";

interface OrderViewProps {
  order: OrderResponse;
}

export default function OrderView({ order: initialOrder }: OrderViewProps) {
  const router = useRouter();
  const [order, setOrder] = useState(initialOrder);
  const [isProcessing, setIsProcessing] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const handleConfirm = async () => {
    setIsProcessing(true);
    setError(null);
    try {
      const updated = await confirmOrder(order.id);
      setOrder(updated);
    } catch (err) {
      console.error("Failed to confirm order:", err);
      setError("Could not confirm order. Please try again.");
    } finally {
      setIsProcessing(false);
    }
  };

  const handleCancel = async () => {
    setIsProcessing(true);
    setError(null);
    try {
      const updated = await cancelOrder(order.id);
      setOrder(updated);
    } catch (err) {
      console.error("Failed to cancel order:", err);
      setError("Could not cancel order. Please try again.");
    } finally {
      setIsProcessing(false);
    }
  };

  return (
    <div className="max-w-3xl mx-auto p-4">
      <div className="flex items-center justify-between mb-6">
        <div>
          <h1 className="text-2xl font-bold">Order {order.orderNumber}</h1>
          <p className="text-gray-500 text-sm">
            Created {formatDate(order.createdAt)}
          </p>
        </div>
        <span
          className={`px-3 py-1 rounded text-sm font-semibold ${
            order.status === "CONFIRMED"
              ? "bg-green-100 text-green-700"
              : order.status === "DRAFT"
              ? "bg-yellow-100 text-yellow-700"
              : order.status === "CANCELLED"
              ? "bg-red-100 text-red-700"
              : "bg-gray-100 text-gray-700"
          }`}
        >
          {order.status}
        </span>
      </div>

      {order.status === "DRAFT" && (
        <p className="text-sm text-gray-600 mb-4">
          This order expires at {formatDate(order.expiresAt)}. Confirm before
          then to secure your tickets.
        </p>
      )}

      <div className="border rounded divide-y">
        {order.items.map((item) => (
          <div
            key={item.ticketTypeId}
            className="flex justify-between items-center p-4"
          >
            <div>
              <p className="font-semibold">{item.ticketTypeName}</p>
              <p className="text-sm text-gray-600">
                {item.quantity} × {item.unitPrice} gel
              </p>
            </div>
            <p className="font-semibold">{item.lineTotal} gel</p>
          </div>
        ))}
      </div>

      <div className="flex justify-between items-center mt-4 text-lg font-bold">
        <span>Total</span>
        <span>{order.totalAmount} gel</span>
      </div>

      {error && <p className="text-red-500 mt-3">{error}</p>}

      {order.status === "DRAFT" && (
        <div className="flex gap-3 mt-6">
          <button
            onClick={handleConfirm}
            disabled={isProcessing}
            className="flex-1 bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 disabled:bg-gray-300"
          >
            {isProcessing ? "Processing..." : "Confirm Order"}
          </button>
          <button
            onClick={handleCancel}
            disabled={isProcessing}
            className="flex-1 bg-white border border-red-500 text-red-500 px-4 py-2 rounded hover:bg-red-50 disabled:opacity-50"
          >
            Cancel Order
          </button>
        </div>
      )}

      {order.status === "CONFIRMED" && (
        <div className="mt-8">
          <h2 className="text-xl font-bold mb-4">Your Tickets</h2>
          <div className="space-y-3">
            {order.tickets.map((ticket) => (
              <div
                key={ticket.id}
                className="border rounded p-4 flex justify-between items-center"
              >
                <div>
                  <p className="font-semibold">{ticket.ticketTypeName}</p>
                  <p className="text-sm text-gray-600">
                    Ticket #{ticket.ticketNumber}
                  </p>
                  {ticket.seatInfo && (
                    <p className="text-sm text-gray-600">{ticket.seatInfo}</p>
                  )}
                </div>
                <span
                  className={`px-2 py-1 rounded text-xs font-semibold ${
                    ticket.status === "VALID"
                      ? "bg-green-100 text-green-700"
                      : "bg-gray-200 text-gray-600"
                  }`}
                >
                  {ticket.status}
                </span>
              </div>
            ))}
          </div>
        </div>
      )}

      {order.status === "CANCELLED" && (
        <p className="text-gray-500 mt-6">This order has been cancelled.</p>
      )}

      <button
        onClick={() => router.push("/orders")}
        className="mt-8 text-blue-500 hover:underline"
      >
        Back to My Orders
      </button>
    </div>
  );
}