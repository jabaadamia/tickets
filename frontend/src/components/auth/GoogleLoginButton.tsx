"use client";
import React from "react";

export default function GoogleLoginButton() {
  const handleLogin = () => {
    window.location.href = `${process.env.NEXT_PUBLIC_BASE_URL}/oauth2/authorization/google`;
  };

  return (
    <button
      type="button"
      onClick={handleLogin}
      className="w-full py-2 bg-amber-500 text-white rounded-lg hover:bg-amber-600 transition"
    >
      Sign in with Google
    </button>
  );
}