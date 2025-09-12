import apiClient from "./client";
import { Event } from "@/types";

export const getEvents = async (): Promise<Event[]> => {
  const response = await apiClient.get<Event[]>(`/events`);
  return response.data;
};

export const getEventById = async (eventId: string): Promise<Event> => {
  const response = await apiClient.get<Event>(`/events/${eventId}`);
  return response.data;
};