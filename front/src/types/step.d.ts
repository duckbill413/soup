export interface StepTitleProps {
  stepNum: number
  title: string
  desc: string
  children?: React.ReactNode
  handleEvent?: Function
  guideTitle?: string
}
