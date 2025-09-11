import Link from "next/link";

export default function Navbar() {
  return (
    <nav className="text-gray-800 p-4 flex justify-between">
      <Link href="/"><div className="font-bold">TicketPlatform</div></Link>
      <div className="space-x-4">
        <Link href="/auth/register">Register</Link>
        <Link href="/auth/login">Login</Link>
      </div>
    </nav>
  );
}
