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