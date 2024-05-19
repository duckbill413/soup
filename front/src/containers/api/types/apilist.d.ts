import { LiveList, LiveObject } from '@liveblocks/client'

export type PathVariable = {
  name: string
  type: number
  desc: string
}

export type QueryParam = {
  name: string
  type: number
  required: boolean
  desc: string
  default: string
}

export type Body = {
  isValid: boolean
  data: string
}

export type APIListDetail = {
  id: string
  domain: string
  name: string
  method_name: string
  http_method: string
  uri: string
  desc: string
  path_variable?: LiveList<LiveObject<PathVariable>>
  query_param?: LiveList<LiveObject<QueryParam>>
  request_body?: LiveObject<Body>
  response_body?: LiveObject<Body>
}
