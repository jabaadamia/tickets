import { fetchEventById, Event } from "@/lib/api/events";
import EventDetail from "@/components/events/EventDetail";

interface PageProps {
  params: { id: string };
}

export default async function EventPage({ params }: PageProps) {
  let event: Event | null = null;

  try {
    event = await fetchEventById(Number(params.id));
  } catch (err) {
    console.error("Failed to fetch event:", err);
  }

  if (!event) {
    return <p className="text-center mt-8 text-red-500">Event not found</p>;
  }

  return <EventDetail event={event} />;
}
