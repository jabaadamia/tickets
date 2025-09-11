import { NextRequest, NextResponse } from 'next/server';

export async function POST(req: NextRequest) {
  const { email, password } = await req.json();

  // TODO: Replace this with real DB check
  if (email === 'test@test.com' && password === '123456') {
    return NextResponse.json({ message: 'Login successful' });
  }

  return NextResponse.json({ message: 'Invalid credentials' }, { status: 401 });
}