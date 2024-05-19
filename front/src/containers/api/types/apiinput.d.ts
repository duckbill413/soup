export interface APIInput {
  title: string
  isEssential: boolean
  placeholder?: string
  options?: Array<string>
  multiline?: boolean
  value?: any
  onChange?: function
}
