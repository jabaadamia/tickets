const BASE_URL = process.env.API_BASE_URL;

export interface Event {
  id: number;
  title: string;
  description: string;
  createdAt: string;
  date: string;
  thumbnailUrl?: string;
  organizer: {
    id: number;
    username: string;
    email: string;
    phoneNumber?: string | null;
    role: string;
    createdAt: string;
  };
  categories: { name: string }[];
  location: {
    name: string;
    address: string;
    city: string;
    latitude: number;
    longitude: number;
  };
}

export const fetchEvents = async () => {
  const response = await fetch(`${BASE_URL}/events`);
  if (!response.ok) {
    throw new Error("Failed to fetch events");
  }
  return response.json();
}

export const fetchEventById = async (id: number) => {
  const response = await fetch(`${BASE_URL}/events/${id}`);
  if (!response.ok) {
    throw new Error("Failed to fetch event");
  }
  return response.json();
}