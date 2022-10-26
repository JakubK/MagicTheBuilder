export interface SignUp {
  username: string;
  email: string;
  password: string;
  password_repeat: string;
  allowNotifications?: boolean;
  allowDataProcessing: boolean;
}
