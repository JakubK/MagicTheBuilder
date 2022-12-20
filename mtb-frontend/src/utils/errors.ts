import { ErrorObject } from "@vuelidate/core";

export const mapErrors = (errors: ErrorObject[]) => {
  if(!errors)
    return;
  return errors.map(x => ({
    message: x.$message.toString(),
  }));
}
