import axios from 'axios'
import type {
  InternalAxiosRequestConfig,
  AxiosResponse,
  AxiosRequestConfig,
  AxiosRequestHeaders,
  AxiosError,
} from 'axios'

interface RequestInterceptors<T> {
  requestInterceptors?: (config: InternalAxiosRequestConfig) => InternalAxiosRequestConfig
  requestInterceptorsCatch?: (err: any) => any
  responseInterceptors?: (config: T) => T
  responseInterceptorsCatch?: (err: any) => any
}

interface RequestConfig<T = AxiosResponse> extends AxiosRequestConfig {
  interceptors?: RequestInterceptors<T>
}

export const PATH_URL = import.meta.env.VITE_API_BASE_PATH || '127.0.0.1:8080'

const axiosInstance = axios.create({
  timeout: 5000,
  baseURL: PATH_URL,
})

const abortControllerMap: Map<string, AbortController> = new Map()

// 请求拦截器
axiosInstance.interceptors.request.use((res: InternalAxiosRequestConfig) => {
  const controller = new AbortController()
  const url = res.url || ''
  res.signal = controller.signal
  abortControllerMap.set(url, controller)
  return res
})

// 响应拦截器
axiosInstance.interceptors.response.use(
  (res: AxiosResponse) => {
    const url = res.config.url || ''
    abortControllerMap.delete(url)
    return res
  },
  (error: AxiosError) => {
    console.log('err:' + error)
    return Promise.reject(error)
  }
)

// 请求封装
const request = (option: AxiosConfig) => {
  const { url, method, params, data, headers, responseType } = option

  return new Promise((resolve, reject) => {
    axiosInstance
      .request({
        url: url,
        method,
        params,
        data: data,
        responseType: responseType,
        headers: {
          Authorization: '',
          ...headers,
        },
      })
      .then((res) => {
        resolve(res)
      })
      .catch((err: any) => {
        reject(err)
      })
  })
}

export default {
  get: <T = any>(option: AxiosConfig) => {
    return request({ method: 'get', ...option }) as Promise<IResponse<T>>
  },
  post: <T = any>(option: AxiosConfig) => {
    return request({ method: 'post', ...option }) as Promise<IResponse<T>>
  },
}
