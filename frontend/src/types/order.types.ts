export type OrderStatus =
  | "DRAFT"
  | "PENDING"
  | "CONFIRMED"
  | "CANCELLED"
  | "COMPLETED"
  | "REFUNDED";

export type TicketStatus = "VALID" | "USED";

export interface CreateOrderItemRequest {
  ticketTypeId: number;
  quantity: number;
}

export interface CreateOrderRequest {
  items: CreateOrderItemRequest[];
  contactEmail?: string;
}

export interface OrderItemResponse {
  ticketTypeId: number;
  ticketTypeName: string;
  quantity: number;
  unitPrice: number;
  lineTotal: number;
}

export interface TicketResponse {
  id: number;
  ticketNumber: string;
  qrCode: string;
  seatInfo: string;
  email: string;
  status: TicketStatus;
  checkedInAt: string | null;
  ticketTypeId: number;
  ticketTypeName: string;
}

export interface OrderResponse {
  id: number;
  orderNumber: string;
  contactEmail: string;
  subTotalAmount: number;
  totalAmount: number;
  status: OrderStatus;
  createdAt: string;
  updatedAt: string;
  expiresAt: string;
  items: OrderItemResponse[];
  tickets: TicketResponse[];
}