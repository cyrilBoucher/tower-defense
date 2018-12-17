package Primitives;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import com.example.td.MyGLRenderer;
import com.example.td.TexturePool;

public class Sprite extends Primitive{

	protected String vertexShaderCode =
            // This matrix member variable provides a hook to manipulate
            // the coordinates of the objects that use this vertex shader
            "uniform mat4 uMVPMatrix;" +
            "attribute vec4 vPosition;" +
            "attribute vec2 aTexCoord;" +
            "varying vec2 vTexCoord;" +
            "void main() {" +
            "  vTexCoord = aTexCoord;" +
            // The matrix must be included as a modifier of gl_Position.
            // Note that the uMVPMatrix factor *must be first* in order
            // for the matrix multiplication product to be correct.
            "  gl_Position = uMVPMatrix * vPosition;" +
            "}";

    protected String fragmentShaderCode =
            "precision mediump float;" +
            "uniform vec4 vColor;" +
            "uniform sampler2D tex;" +
            "varying vec2 vTexCoord;" +
            "void main() {" +
            "  gl_FragColor = texture2D(tex, vTexCoord);" +
            "}";
    
    protected  FloatBuffer vertexBuffer;
    protected  ShortBuffer drawListBuffer;
    protected  FloatBuffer texCoordBuffer;
    private  int mProgram;
    private int mPositionHandle;
    private int mColorHandle;
    private int mMVPMatrixHandle;
    private int mTexCoordHandle;
    private int mTextureHandle;
	
	// number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    static final int TEXCOORDS_PER_VERTEX = 2;
    protected float primCoords[];
    protected float primTexCoords[];
    
    private boolean initialised = false;

    protected short drawOrder[]; // order to draw vertices

    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex
    private final int texStride = TEXCOORDS_PER_VERTEX * 4; // 4 bytes per vertex

    protected float primColor[];
    
    private int texturename;
    
    public Sprite(float x, float y, float width, float height, String[] file_names)
    {
    	drawOrder = new short [] { 0, 1, 2, 0, 2, 3 };
    	
    	primColor = new float[]{1.0f, 1.0f, 1.0f, 1.0f};
    	
    	primCoords = new float[] {
                x-width/2f,  y+height/2f, 0.0f,   // top left
                x-width/2f, y-height/2f, 0.0f,   // bottom left
                 x+width/2f, y-height/2f, 0.0f,   // bottom right
                 x+width/2f,  y+height/2f, 0.0f }; // top right	
    	
    	// Create our UV coordinates.
    	primTexCoords = new float[] {
                0.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 1.0f,
                1.0f, 0.0f
        };
    	
    	texturename = TexturePool.getTexture(file_names[0]);
    }
    
    public void init()
    {
    	// initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(
        // (# of coordinate values * 4 bytes per float)
        		primCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(primCoords);
        vertexBuffer.position(0);

        // initialize byte buffer for the draw list
        ByteBuffer dlb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 2 bytes per short)
                drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);
        
     // initialize vertex byte buffer for texture coordinates
        ByteBuffer tbb = ByteBuffer.allocateDirect(
        // (# of coordinate values * 4 bytes per float)
        		primTexCoords.length * 4);
        tbb.order(ByteOrder.nativeOrder());
        texCoordBuffer = tbb.asFloatBuffer();
        texCoordBuffer.put(primTexCoords);
        texCoordBuffer.position(0);

        // prepare shaders and OpenGL program
        int vertexShader = MyGLRenderer.loadShader(
                GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(
                GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        mProgram = GLES20.glCreateProgram();             // create empty OpenGL Program
        GLES20.glAttachShader(mProgram, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(mProgram, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(mProgram);                  // create OpenGL program executables
    }
    
    public void draw(float[] mvpMatrix) {
    	// Initialise buffers
    	if(!initialised)
    	{
    		init();
    		initialised = true;
    	}
    	
    	// Add program to OpenGL environment
        GLES20.glUseProgram(mProgram);

        // get handle to vertex shader's vPosition member
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(
                mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);
        
        // get handle to vertex shader's aTexCoord member
        mTexCoordHandle = GLES20.glGetAttribLocation(mProgram, "aTexCoord");

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mTexCoordHandle);

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(
        		mTexCoordHandle, TEXCOORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                texStride, texCoordBuffer);

        // get handle to fragment shader's vColor member
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

        // Set color for drawing the triangle
        GLES20.glUniform4fv(mColorHandle, 1, primColor, 0);

        // get handle to shape's transformation matrix
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        MyGLRenderer.checkGlError("glGetUniformLocation");

        // Apply the projection and view transformation
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
        MyGLRenderer.checkGlError("glUniformMatrix4fv");
        
        // get handle to fragment shader's tex member
        mTextureHandle = GLES20.glGetUniformLocation(mProgram, "tex");

        // Set color for drawing the triangle
        GLES20.glUniform1i(mTextureHandle, 0);
        
     // Bind texture to texturename
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texturename);

        // Draw elements using triangles
        drawElements();

        // Disable vertex and texture coordinates array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
        GLES20.glDisableVertexAttribArray(mTexCoordHandle);
    }
    
    public void drawElements()
    {
    	// Draw elements using triangles
        GLES20.glDrawElements(
        		GLES20.GL_TRIANGLES, drawOrder.length,
                GLES20.GL_UNSIGNED_SHORT, drawListBuffer);
    }

}
