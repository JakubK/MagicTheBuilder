export interface SignUp {
  email: string;
  password: string;
  passwordRepeat: string;
  allowNotifications?: boolean;
  allowDataProcessing: boolean;
}
