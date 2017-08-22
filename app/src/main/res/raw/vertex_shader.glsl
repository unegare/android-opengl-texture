attribute vec4 a_Position;
attribute vec2 a_texcoords;
varying vec2 v_texcoords;

void main () {
    gl_Position = a_Position;
    v_texcoords = a_texcoords;
}