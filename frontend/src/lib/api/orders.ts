import apiClient from "./client";
import {
  CreateOrderRequest,
  OrderResponse,
} from "@/types";

export const createDraftOrder = async (
  orderData: CreateOrderRequest
): Promise<OrderResponse> => {
  const response = await apiClient.post<OrderResponse>(`/orders`, orderData);
  return response.data;
};

export const confirmOrder = async (orderId: number): Promise<OrderResponse> => {
  const response = await apiClient.post<OrderResponse>(`/orders/${orderId}/confirm`);
  return response.data;
};

export const cancelOrder = async (orderId: number): Promise<OrderResponse> => {
  const response = await apiClient.post<OrderResponse>(`/orders/${orderId}/cancel`);
  return response.data;
};

export const getMyOrders = async (): Promise<OrderResponse[]> => {
  const response = await apiClient.get<OrderResponse[]>(`/orders/my`);
  return response.data;
};

export const getMyOrderById = async (orderId: number): Promise<OrderResponse> => {
  const response = await apiClient.get<OrderResponse>(`/orders/${orderId}`);
  return response.data;
};