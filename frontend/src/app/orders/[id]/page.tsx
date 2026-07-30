"use client";

import React, { useEffect, useState } from "react";
import { useParams } from "next/navigation";
import { getMyOrderById } from "@/lib/api/orders";
import { OrderResponse } from "@/types";
import OrderView from "@/components/orders/OrderView";

export default function OrderPage() {
  const params = useParams();
  const id = Number(params.id);

  const [order, setOrder] = useState<OrderResponse | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [notFound, setNotFound] = useState(false);

  useEffect(() => {
    let isMounted = true;

    getMyOrderById(id)
      .then((data) => {
        if (isMounted) setOrder(data);
      })
      .catch((err) => {
        console.error("Failed to fetch order:", err);
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
    return <p className="text-center mt-8 text-gray-500">Loading order...</p>;
  }

  if (notFound || !order) {
    return <p className="text-center mt-8 text-red-500">Order not found</p>;
  }

  return <OrderView order={order} />;
}