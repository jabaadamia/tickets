import React from "react";
import Link from "next/link";
import { OrderResponse } from "@/types";
import { formatDate } from "@/lib/formatters";

interface OrderCardProps {
  order: OrderResponse;
}

const statusStyles: Record<string, string> = {
  DRAFT: "bg-yellow-100 text-yellow-700",
  PENDING: "bg-yellow-100 text-yellow-700",
  CONFIRMED: "bg-green-100 text-green-700",
  CANCELLED: "bg-red-100 text-red-700",
  COMPLETED: "bg-blue-100 text-blue-700",
  REFUNDED: "bg-gray-100 text-gray-700",
};

export default function OrderCard({ order }: OrderCardProps) {
  return (
    <Link
      href={`/orders/${order.id}`}
      className="block border rounded p-4 hover:bg-gray-50 transition"
    >
      <div className="flex justify-between items-center">
        <div>
          <p className="font-semibold">{order.orderNumber}</p>
          <p className="text-sm text-gray-500">
            {formatDate(order.createdAt)}
          </p>
        </div>
        <div className="flex items-center gap-4">
          <p className="font-semibold">{order.totalAmount} gel</p>
          <span
            className={`px-3 py-1 rounded text-xs font-semibold ${
              statusStyles[order.status] ?? "bg-gray-100 text-gray-700"
            }`}
          >
            {order.status}
          </span>
        </div>
      </div>
    </Link>
  );
}