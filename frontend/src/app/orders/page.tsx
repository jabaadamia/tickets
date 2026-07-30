"use client";

import React, { useEffect, useState } from "react";
import Link from "next/link";
import { getMyOrders } from "@/lib/api/orders";
import { OrderResponse } from "@/types";
import OrderCard from "@/components/orders/OrderCard";

export default function MyOrdersPage() {
  const [orders, setOrders] = useState<OrderResponse[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    let isMounted = true;

    getMyOrders()
      .then((data) => {
        if (isMounted) setOrders(data);
      })
      .catch((err) => {
        console.error("Failed to fetch orders:", err);
        if (isMounted) setError("Could not load your orders.");
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
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold">My Orders</h1>
        {/* Placeholder link — /tickets page not built yet */}
        <Link href="/tickets" className="text-blue-500 hover:underline text-sm">
          See My Tickets
        </Link>
      </div>

      {isLoading && <p className="text-gray-500">Loading orders...</p>}
      {error && <p className="text-red-500">{error}</p>}

      {!isLoading && !error && orders.length === 0 && (
        <p className="text-gray-500">You haven't placed any orders yet.</p>
      )}

      <div className="space-y-3">
        {orders.map((order) => (
          <OrderCard key={order.id} order={order} />
        ))}
      </div>
    </div>
  );
}