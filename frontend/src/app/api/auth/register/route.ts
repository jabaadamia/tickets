import { NextRequest, NextResponse } from 'next/server';

export async function POST(req: NextRequest) {
  const { username, email, password } = await req.json();

  // TODO: Replace this with real DB logic
  console.log('Registering user:', { username, email, password });

  return NextResponse.json({ message: 'User registered' });
}