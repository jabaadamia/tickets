import apiClient from "./client";
import { TicketResponse, TicketScanRequest } from "@/types";
import { MessageResponse } from "@/types";

export const getMyTickets = async (): Promise<TicketResponse[]> => {
  const response = await apiClient.get<TicketResponse[]>(`/tickets/my`);
  return response.data;
};

export const getMyTicketById = async (
  ticketId: number
): Promise<TicketResponse> => {
  const response = await apiClient.get<TicketResponse>(`/tickets/${ticketId}`);
  return response.data;
};

export const verifyAndCheckInTicket = async (
  scanData: TicketScanRequest
): Promise<MessageResponse> => {
  const response = await apiClient.post<MessageResponse>(
    `/tickets/verify-and-check-in`,
    scanData
  );
  return response.data;
};