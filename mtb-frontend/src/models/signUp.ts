export interface SignUp {
  username: string;
  email: string;
  password: string;
  passwordRepeat: string;
  allowNotifications?: boolean;
  allowDataProcessing: boolean;
}
