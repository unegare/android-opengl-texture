package com.example.garet.myapplicationopengl003;

import android.content.Context;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_UNSIGNED_SHORT;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDisableVertexAttribArray;
import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;

/**
 * Created by garet on 19.08.17.
 */

public class CameraFrame {

    private String TAG = "CameraFrame";
    private int mProgram;
    private Context context;

    private int a_PositionHandle;
    private int a_texcoordsHandle;
    private int drawOrderHandle;

    private final float[] a_PositionCoords = {
            -1.0f, 1.0f, 0.0f,
            -1.0f, -1.0f, 0.0f,
            1.0f, -1.0f, 0.0f,
            1.0f, 1.0f, 0.0f
    };

    private final float[] a_texcoords = {
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f
    };

    private FloatBuffer a_PositionBuffer;
    private FloatBuffer a_texcoordsBuffer;
    private ShortBuffer drawOrderBuffer;

    private int texture;

    static final short drawOrder[] = { 0, 1, 2, 0, 2, 3 };

    public CameraFrame (Context context) {
        this.context = context;
        int vertexShader = ShaderUtils.createShader(context, GL_VERTEX_SHADER, R.raw.vertex_shader);
        int fragmentShader = ShaderUtils.createShader(context, GL_FRAGMENT_SHADER, R.raw.fragment_shader);
        mProgram = ShaderUtils.createProgram(vertexShader, fragmentShader);

        a_PositionBuffer = ByteBuffer
                .allocateDirect(a_PositionCoords.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        if (a_PositionBuffer == null) {
            throw new RuntimeException("a_PositionBuffer == null");        }
        a_PositionBuffer.put(a_PositionCoords);

        a_texcoordsBuffer = ByteBuffer
                .allocateDirect(a_texcoords.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        if (a_texcoordsBuffer == null) {
            throw new RuntimeException("a_texcoordsBuffer == null");
        }
        a_texcoordsBuffer.put(a_texcoords);

        drawOrderBuffer = ByteBuffer
                .allocateDirect(drawOrder.length * 2)
                .order(ByteOrder.nativeOrder())
                .asShortBuffer();
        if (drawOrderBuffer == null) {
            throw new RuntimeException("drawOrderBuffer == null");
        }
        drawOrderBuffer.put(drawOrder);

        texture = TextureUtils.loadTexture(context, R.drawable.texture);

        a_PositionBuffer.position(0);
        a_texcoordsBuffer.position(0);
        drawOrderBuffer.position(0);
    }

    public void draw () {
        glUseProgram(mProgram);

        a_PositionBuffer.position(0);
        a_PositionHandle = glGetAttribLocation(mProgram, "a_Position");
        glVertexAttribPointer(a_PositionHandle, 3, GL_FLOAT, false, 12, a_PositionBuffer);
        glEnableVertexAttribArray(a_PositionHandle);

        a_texcoordsBuffer.position(0);
        a_texcoordsHandle = glGetAttribLocation(mProgram, "a_texcoords");
        glVertexAttribPointer(a_texcoordsHandle, 2, GL_FLOAT, false, 8, a_texcoordsBuffer);
        glEnableVertexAttribArray(a_texcoordsHandle);

        drawOrderBuffer.position(0);
        glBindTexture(GL_TEXTURE_2D, texture);
        glDrawElements(GL_TRIANGLES, drawOrder.length, GL_UNSIGNED_SHORT, drawOrderBuffer);

        glDisableVertexAttribArray(a_PositionHandle);
        glDisableVertexAttribArray(a_texcoordsHandle);
    }
}
