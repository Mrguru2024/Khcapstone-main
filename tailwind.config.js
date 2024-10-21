const {plugins} = require("./postcss.config");
module.exports = {
  content: [
    './src/main/resources/templates/**/*.html', // For Thymeleaf templates
    './src/main/resources/static/**/*.js',      // For static JS files
    './src/main/resources/static/**/*.css',     // If you have any custom CSS
    './src/**/*.js',                            // For JavaScript files in the src directory
  ],
  theme: {
    extend: {
      colors: {
        // Custom futuristic color palette
        'neon-green': '#3fffd9',
        'dark-cyber': '#1b1b2f',
        'steel-gray': '#3a3f44',
        'bright-blue': '#2f89fc',
        'dark-blue': '#0e153a',
        'soft-white': '#e0f0ea',
      },
      fontFamily: {
        // Custom futuristic fonts (you can use Google Fonts or system fonts)
        futuristic: ['"Orbitron"', 'sans-serif'],
        body: ['"Inter"', 'sans-serif'],
      },
    },
  },
  plugins: [],
};

