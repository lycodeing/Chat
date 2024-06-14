declare interface AxiosConfig {
  params?: any
  data?: any
  url?: string
  method?: AxiosMethod
  headers?: RawAxiosRequestHeaders
  responseType?: AxiosResponseType
}

declare interface IResponse<T = any> {
  code: number
  data: T extends any ? T : T & any
}
