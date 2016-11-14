var webpack = require('webpack');
var HtmlWebpackPlugin = require('html-webpack-plugin');
var ExtractTextPlugin = require('extract-text-webpack-plugin');

module.exports = {
  devtool: 'eval',
  devServer: { hot: true },
  entry: ['./src/js/index.jsx'],
  output: {
    path: __dirname + '/dist',
    filename: './bundle.js'
  },
  module: {
    loaders: [
      {
        test: /\.scss$/,
        loader: ExtractTextPlugin.extract('css!sass')
      },
      {
        test: /\.jsx$/,
        loader: 'babel'
      }
    ]
  },
  plugins: [
    new ExtractTextPlugin('styles.css', {
      allChunks: true
    }),
    new HtmlWebpackPlugin({
      template: './src/template/index.html'
    }),
    new webpack.HotModuleReplacementPlugin()
  ]
};
