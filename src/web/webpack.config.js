const webpack = require('webpack');
const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const LodashModuleReplacementPlugin = require('lodash-webpack-plugin');
const BundleAnalyzerPlugin = require('webpack-bundle-analyzer').BundleAnalyzerPlugin;
const LinkTypePlugin = require('html-webpack-link-type-plugin').HtmlWebpackLinkTypePlugin;
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const {CleanWebpackPlugin} = require('clean-webpack-plugin');
const CssMinimizerPlugin = require('css-minimizer-webpack-plugin');
const ImageMinimizerPlugin = require('image-minimizer-webpack-plugin');
const JsonMinimizerPlugin = require("json-minimizer-webpack-plugin");

const distFolder = '../../build/webpack/'

const tempates = [ "login", "bot-dashboard"]

const config = {
    entry: {
        main: './script/entry/main.ts',
        login: './script/entry/login.ts',
    },
    output: {
        path: path.resolve(__dirname, distFolder),
        publicPath: '/',
        filename: 'resources/script/[name].[contenthash].js',
        assetModuleFilename: 'resources/assets/[contenthash][ext][query]'
    },
    module: {
        rules: [
            {
                test: /\.(js|jsx)$/,
                use: 'babel-loader',
                exclude: /node_modules/
            },
            {
                test: /\.(sa|sc|c)ss$/,
                use: [
                    MiniCssExtractPlugin.loader,
                    'css-loader',
                    'postcss-loader',
                    'sass-loader'
                ],
            },
            {
                test: /\.ts(x)?$/,
                //loader: 'ts-loader',
                use: [
                    'babel-loader',
                    {
                        loader: 'ts-loader',
                        options: {
                            //configFile: "./tsconfig.json"
                        }
                    }
                ],
                exclude: /node_modules/
            },
            {

                test: /\.json/,
                type: 'asset/resource'

            },
            {

                test: /\.(png|svg|jpg|jpeg|gif)/,
                type: 'asset/resource'

            },
            {

                test: /\.(woff|woff2|eot|ttf|otf)$/i,

                type: 'asset/resource',

            },
        ]
    },
    resolve: {
        extensions: [
            '.tsx',
            '.ts',
            '.js'
        ],
        alias: {
            '$': 'jquery'
        }
    },
    plugins: [
        new webpack.ContextReplacementPlugin(/moment[\/\\]locale$/, /en/),
        new HtmlWebpackPlugin({
            filename: 'templates/login.html',
            template: './html/login.html',
            scriptLoading: 'defer',
            inject: 'head',
            appMountId: 'main',
            chunks: ["login"],
            minify: true,
            cache: true,
            hash: true,
        }),
        /*new HtmlWebpackPlugin({
            filename: 'templates/dashboard.html',
            template: './html/dashboard.html',
            scriptLoading: 'defer',
            inject: 'head',
            appMountId: 'main',
            chunks: ["main"],
            minify: true,
            cache: true,
            hash: true,
        }),
        new HtmlWebpackPlugin({
            filename: 'templates/login.html',
            template: './html/login.html',
            scriptLoading: 'defer',
            inject: 'head',
            appMountId: 'main',
            chunks: ["login"],
            minify: true,
            cache: true,
            hash: true,
        }),
        new HtmlWebpackPlugin({
            filename: 'templates/login.html',
            template: './html/login.html',
            scriptLoading: 'defer',
            inject: 'head',
            appMountId: 'main',
            chunks: ["login"],
            minify: true,
            cache: true,
            hash: true,
        }),*/
        new ImageMinimizerPlugin({
            minimizerOptions: {
                // Lossless optimization with custom option
                // Feel free to experiment with options for better result for you
                plugins: [
                    ['gifsicle', {interlaced: true}],
                    ['jpegtran', {progressive: true}],
                    ['optipng', {optimizationLevel: 5}],
                    [
                        'svgo',
                        {
                            plugins: [
                                {
                                    removeViewBox: false,
                                },
                            ],
                        },
                    ],
                ],
            },
        }),
        new webpack.DefinePlugin({
            MUSICNOVA_VERSION: JSON.stringify('0.0.1-SNAPSHOT')
        }),
        new LodashModuleReplacementPlugin,
        new BundleAnalyzerPlugin({
            analyzerMode: 'static',
            openAnalyzer: false,
        }),
        new MiniCssExtractPlugin({
            chunkFilename: 'resources/styles/' + ('[id].[contenthash].css'),
            filename: 'resources/styles/' + ('[name].[contenthash].css'),
        }),
        new CleanWebpackPlugin(),
        new LinkTypePlugin(),
    ],
    optimization: {
        runtimeChunk: 'single',
        splitChunks: {
            cacheGroups: {
                vendor: {
                    test: /[\\/]node_modules[\\/]/,
                    name: 'vendors',
                    chunks: 'all'
                }
            }
        },
        minimizer: [
            `...`,
            new CssMinimizerPlugin({
                cache: true,
                sourceMap: true,
                minimizerOptions: {
                    preset: [
                        'default',
                        {
                            discardComments: {removeAll: true},
                        },
                    ],
                }
            }),
            new JsonMinimizerPlugin(),
        ],
    },
    devServer: {
        contentBase: distFolder
    }
};

module.exports = (env, argv) => {
    if (argv.hot) {
        // Cannot use 'contenthash' when hot reloading is enabled.
        config.output.filename = '[name].[hash].js';
    }
    tempates.forEach((template) => {
        config.plugins.unshift(new HtmlWebpackPlugin({
            filename: 'templates/' + template + '.html',
            template: './html/' + template + '.html',
            scriptLoading: 'defer',
            inject: 'head',
            appMountId: 'main',
            chunks: ["main"],
            minify: true,
            cache: true,
            hash: true,
        }))
    })


    return config;
};