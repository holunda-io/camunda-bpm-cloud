'use strict';

module.exports = function (grunt) {

  // Time how long tasks take. Can help when optimizing build times
  require('time-grunt')(grunt);

  // Automatically load required Grunt tasks
  require('jit-grunt')(grunt, {
    ngtemplates: 'grunt-angular-templates',
    configureProxies: 'grunt-connect-proxy',
    newer: 'grunt-newer'
  });

  // Configurable paths for the application
  var appConfig = {
    app: 'frontend/app',
    dist: 'src/main/resources/static',
    tmp: 'target/css'
  };

  // Define the configuration for all the tasks
  grunt.initConfig({

    // Project settings
    yeoman: appConfig,

    // Watches files for changes and runs tasks based on the changed files
    watch: {
      js: {
        files: ['<%= yeoman.app %>/scripts/{,*/}*.js'],
        tasks: ['clean:dist, newer:jshint:all', 'newer:jscs:all', 'ngtemplates', 'browserify:dist', 'copy:dist'],
        options: {
          livereload: '<%= connect.options.livereload %>'
        }
      },
      index: {
        files: ['frontend/index.html'],
        tasks: ['copy:dist'],
        options: {livereload: true}
      },
      views: {
        files: ['<%= yeoman.app %>/views/{,*/}*.html'],
        tasks: ['ngtemplates', 'copy:dist'],
        options: {livereload: true}
      },
      styles: {
        files: ['<%= yeoman.app %>/styles/{,*/}*.css'],
        tasks: ['newer:copy:styles', 'postcss']
      },
      gruntfile: {
        files: ['Gruntfile.js']
      },
      livereload: {
        options: {
          livereload: '<%= connect.options.livereload %>'
        },
        files: [
          '<%= yeoman.app %>/{,*/}*.html',
          '<%= yeoman.tmp %>/styles/{,*/}*.css',
          '<%= yeoman.app %>/images/{,*/}*.{png,jpg,jpeg,gif,webp,svg}'
        ]
      }
    },

    // The actual grunt server settings
    connect: {
      options: {
        port: 9001,
        // Change this to '0.0.0.0' to access the server from outside.
        hostname: 'localhost',
        livereload: 35729
      },
      proxies: [
        {
          context: ['/cloud'],
          host: process.env.DOCKER_IP || 'localhost',
          port: 8082,
          https: false,
          changeOrigin: false,
          rewrite: {
            '^/cloud': '/eventservice'
          }
        }
      ],
      dist: {
        options: {
          open: true,
          base: '<%= yeoman.dist %>',
          middleware: function (connect, options) {
            if (!Array.isArray(options.base)) {
              options.base = [options.base];
            }

            // Setup the proxy
            var middlewares = [require('grunt-connect-proxy/lib/utils').proxyRequest];

            // Serve static files.
            options.base.forEach(function (base) {
              middlewares.push(require('st')({
                path: base,
                url: '/',
                index: 'index.html', passthrough: true, gzip: false, cache: false
              }));
            });
            return middlewares;
          }
        }
      }
    },

    // Make sure there are no obvious mistakes
    jshint: {
      options: {
        jshintrc: '.jshintrc',
        reporter: require('jshint-stylish')
      },
      all: {
        src: [
          'Gruntfile.js',
          '<%= yeoman.app %>/scripts/{,*/}*.js'
        ]
      }
    },

    // Make sure code styles are up to par
    jscs: {
      options: {
        config: '.jscsrc',
        verbose: true
      },
      all: {
        src: [
          'Gruntfile.js',
          '<%= yeoman.app %>/scripts/{,*/}*.js'
        ]
      }
    },

    // Empties folders to start fresh
    clean: {
      dist: {
        files: [{
          dot: true,
          src: [
            '<%= yeoman.tmp %>',
            '<%= yeoman.dist %>/{,*/}*',
            '!<%= yeoman.dist %>/.git{,*/}*',
          ]
        }]
      }
    },

    // Add vendor prefixed styles
    postcss: {
      options: {
        processors: [
          require('autoprefixer-core')({browsers: ['last 1 version']})
        ]
      },
      dist: {
        files: [{
          expand: true,
          cwd: '<%= yeoman.tmp %>/styles/',
          src: '{,*/}*.css',
          dest: '<%= yeoman.dist %>/styles/'
        }]
      }
    },

    browserify: {
      options: {
        transform: [['babelify', {presets: ['es2015']}]]
      },
      dist: {
        files: {
          // if the source file has an extension of es6 then
          // we change the name of the source file accordingly.
          // The result file's extension is always .js
          '<%= yeoman.dist %>/app.js': ['<%= yeoman.app %>/scripts/app.js', '<%= yeoman.app %>/scripts/app.constant.js']
        }
      }
    },

    ngtemplates: {
      dist: {
        options: {
          module: 'simpleProcess',
          htmlmin: {
            collapseWhitespace: true,
            conservativeCollapse: true,
            collapseBooleanAttributes: true,
            removeCommentsFromCDATA: true
          },
        },
        cwd: '<%= yeoman.app %>',
        src: 'views/{,*/}*.html',
        dest: '<%= yeoman.dist %>/templates.js'
      }
    },

    // Copies remaining files to places other tasks can use
    copy: {
      dist: {
        files: [
          {
            src: 'frontend/index.html',
            dest: '<%= yeoman.dist %>/index.html'
          },
          {
            cwd: 'node_modules/bootstrap/dist',
            src: '**/*',
            dest: '<%= yeoman.dist %>/bootstrap',
            expand: true
          },
          {
            cwd: '<%= yeoman.app %>/images',
            src: '**/*',
            dest: '<%= yeoman.dist %>/images',
            expand: true
          }
        ]
      },
      styles: {
        expand: true,
        cwd: '<%= yeoman.app %>/styles',
        dest: '<%= yeoman.tmp %>/styles/',
        src: '{,*/}*.css'
      }
    }
  });


  grunt.registerTask('serve', 'Compile then start a connect web server', function () {
    return grunt.task.run([
      'build',
      'configureProxies:server',
      'connect:dist',
      'watch'
    ]);
  });

  grunt.registerTask('build', [
    'clean:dist',
    'ngtemplates',
    'copy:styles',
    'postcss',
    'browserify:dist',
    'copy:dist'
  ]);

  grunt.registerTask('default', [
    'build'
  ]);
};
