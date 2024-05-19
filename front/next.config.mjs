import { createVanillaExtractPlugin } from '@vanilla-extract/next-plugin'
import path from 'path'

const withVanillaExtract = createVanillaExtractPlugin()

/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: false,
  webpack: (config) => {
    const fileLoaderRule = config.module.rules.find((rule) =>
      rule.test?.test?.('.svg'),
    )

    config.resolve.alias = {
      ...(config.resolve.alias || {}),
      '@': path.resolve(process.cwd(), 'src/'),
    }

    config.module.rules.push(
      {
        ...fileLoaderRule,
        test: /\.svg$/i,
        resourceQuery: { not: /components/ },
      },
      {
        test: /\.svg$/i,
        issuer: /\.[jt]sx?$/,
        resourceQuery: /components/,
        use: ['@svgr/webpack'],
      },
    )

    return config
  },
  images: {
    domains: [
      'placehold.co',
      'prod-files-secure.s3.us-west-2.amazonaws.com',
      'file.notion.so',
    ],
  },
  output: 'standalone',
}

export default withVanillaExtract(nextConfig)
