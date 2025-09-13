"use client";

import { AuthContextType } from "@/types";
import { createContext, useContext, useEffect, useState, ReactNode } from "react";

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export function AuthProvider({ children }: { children: ReactNode }) {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [role, setRole] = useState<string | null>(null);
  const [token, setToken] = useState<string | null>(null);

  const decodeToken = (jwt: string) => {
    try {
      const payload = JSON.parse(atob(jwt.split(".")[1]));
      return payload;
    } catch (e) {
      console.error("Invalid JWT", e);
      return null;
    }
  };

  useEffect(() => {
    const storedToken = localStorage.getItem("accessToken");
    if (storedToken) {
      const payload = decodeToken(storedToken);
      if (payload) {
        setToken(storedToken);
        const resolvedRole = payload.roles ? payload.roles[0] : null;
        setRole(resolvedRole);
        setIsLoggedIn(true);
      }
    }
  }, []);

  const login = (newToken: string) => {
    localStorage.setItem("accessToken", newToken);
    const payload = decodeToken(newToken);
    setToken(newToken);
    const resolvedRole = payload?.roles ? payload.roles[0] : payload.role || null;
    setRole(resolvedRole);
    setIsLoggedIn(true);
  };

  const logout = () => {
    localStorage.removeItem("accessToken");
    setToken(null);
    setRole(null);
    setIsLoggedIn(false);
  };

  return (
    <AuthContext.Provider value={{ isLoggedIn, role, token, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuth must be used within AuthProvider");
  }
  return context;
}