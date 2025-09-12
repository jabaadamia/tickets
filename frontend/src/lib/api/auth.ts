import { RegisterRequest, LoginRequest, AuthResponse } from "@/types";
import apiClient from "./client";
import { AxiosResponse } from "axios";


export const register = async (
  userData: RegisterRequest
): Promise<AxiosResponse<string>> => {
  console.log("Registering user with data:", userData);
  return apiClient.post<string>("/auth/register", userData);
};

export const login = async (
  userData: LoginRequest
): Promise<AxiosResponse<AuthResponse>> => {
  console.log("Logging in user with data:", userData);
  return apiClient.post<AuthResponse>("/auth/login", userData);
};
