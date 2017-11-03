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
  var config = {
    src: 'src',
    app: 'src/app',
    dist: 'target/grunt',
    tmp: 'target/css',
    moduleName: 'cloudTasklistApp'
  };

  // Define the configuration for all the tasks
  grunt.initConfig({

    // Project settings
    build: config,

    // Watches files for changes and runs tasks based on the changed files
    watch: {
      js: {
        files: ['<%= build.app %>/scripts/{,*/}*.js'],
        tasks: ['newer:jshint:all', 'newer:jscs:all', 'browserify:dist'],
        options: {
          livereload: '<%= connect.options.livereload %>'
        }
      },
      index: {
        files: ['<%= build.src %>/index.html'],
        tasks: ['copy:dist'],
        options: {livereload: true}
      },
      views: {
        files: ['<%= build.app %>/views/{,*/}*.html'],
        tasks: ['ngtemplates', 'copy:dist'],
        options: {livereload: true}
      },
      styles: {
        files: ['<%= build.app %>/styles/{,*/}*.css'],
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
          '<%= build.app %>/{,*/}*.html',
          '<%= build.tmp %>/styles/{,*/}*.css',
          '<%= build.app %>/images/{,*/}*.{png,jpg,jpeg,gif,webp,svg}'
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
          base: '<%= build.dist %>',
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
          '<%= build.app %>/scripts/{,*/}*.js'
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
          '<%= build.app %>/scripts/{,*/}*.js'
        ]
      }
    },

    // Empties folders to start fresh
    clean: {
      dist: {
        files: [{
          dot: true,
          src: [
            '<%= build.tmp %>',
            '<%= build.dist %>/{,*/}*',
            '!<%= build.dist %>/.git{,*/}*',
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
          cwd: '<%= build.tmp %>/styles/',
          src: '{,*/}*.css',
          dest: '<%= build.dist %>/styles/'
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
          '<%= build.dist %>/app.js': ['<%= build.app %>/scripts/app.js', '<%= build.app %>/scripts/app.constant.js']
        }
      }
    },

    ngtemplates: {
      dist: {
        options: {
          module: '<%= build.moduleName %>',
          htmlmin: {
            collapseWhitespace: true,
            conservativeCollapse: true,
            collapseBooleanAttributes: true,
            removeCommentsFromCDATA: true
          },
        },
        cwd: '<%= build.app %>',
        src: 'views/{,*/}*.html',
        dest: '<%= build.dist %>/templates.js'
      }
    },

    // Copies remaining files to places other tasks can use
    copy: {
      dist: {
        files: [
          {
            src: '<%= build.src %>/index.html',
            dest: '<%= build.dist %>/index.html'
          },
          {
            cwd: 'node_modules/bootstrap/dist',
            src: '**/*',
            dest: '<%= build.dist %>/bootstrap',
            expand: true
          },
          {
            cwd: '<%= build.app %>/images',
            src: '**/*',
            dest: '<%= build.dist %>/images',
            expand: true
          }
        ]
      },
      styles: {
        expand: true,
        cwd: '<%= build.app %>/styles',
        dest: '<%= build.tmp %>/styles/',
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
