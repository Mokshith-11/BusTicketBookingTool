/*
 * Beginner guide:
 * - This model describes the common API response shape returned by Spring Boot.
 * - Using a TypeScript interface helps components know that responses contain statusCode, message, and data.
 * - It makes frontend code safer because fields are typed instead of guessed.
 */
export interface ApiResponse<T> {
  statusCode: number;
  message: string;
  data: T;
}
