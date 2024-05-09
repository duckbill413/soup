export interface Option {
  id: number
  value: string
}

export interface APIInput {
  title: string
  isEssential: boolean
  placeholder?: string
  options?: Array<Option>
  multiline?: boolean
  value?: any
  onChange?: function
}
