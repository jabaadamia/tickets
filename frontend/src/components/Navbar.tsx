"use client";

import { useAuth } from "@/context/AuthContext";
import Link from "next/link";
import Logout from "./auth/Logout";

export default function Navbar() {
  const { isLoggedIn, role } = useAuth();
  return (
    <nav className="text-PrimaryDark p-4 flex justify-between">
      <Link href="/"><div className="font-bold">TicketPlatform</div></Link>
      <div className="space-x-4">
        {isLoggedIn ?
        (<Logout />) : 
        (
          <>
            <Link href="/auth/register">Register</Link>
            <Link href="/auth/login">Login</Link>
          </>
        )}
      </div>
    </nav>
  );
}
