package engine.graphics;

import static org.lwjgl.opengl.GL46C.*;

import engine.utils.FileUtils;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Shader {
	private String vertexFile, fragmentFile;
	private int vertexID, fragmentID, programID;
	
	public Shader(String vertexPath, String fragmentPath) {
		vertexFile = FileUtils.loadAsString(vertexPath);
		fragmentFile = FileUtils.loadAsString(fragmentPath);
	}
	
	public void create() {
		programID = glCreateProgram();
		vertexID = glCreateShader(GL_VERTEX_SHADER);
		
		glShaderSource(vertexID, vertexFile);
		glCompileShader(vertexID);
		
		if (glGetShaderi(vertexID, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println("Vertex Shader: " + glGetShaderInfoLog(vertexID));
			return;
		}
		
		fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
		
		glShaderSource(fragmentID, fragmentFile);
		glCompileShader(fragmentID);
		
		if (glGetShaderi(fragmentID, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println("Fragment Shader: " + glGetShaderInfoLog(fragmentID));
			return;
		}
		
		glAttachShader(programID, vertexID);
		glAttachShader(programID, fragmentID);
		
		glLinkProgram(programID);
		if (glGetProgrami(programID, GL_LINK_STATUS) == GL_FALSE) {
			System.err.println("Program Linking: " + glGetProgramInfoLog(programID));
			return;
		}
		
		glValidateProgram(programID);
		if (glGetProgrami(programID, GL_VALIDATE_STATUS) == GL_FALSE) {
			System.err.println("Program Validation: " + glGetProgramInfoLog(programID));
			return;
		}
		
		glDeleteShader(vertexID);
		glDeleteShader(fragmentID);
	}
	
	public void bind() {
		glUseProgram(programID);
	}
	
	void unbind() {
		glUseProgram(0);
	}

	public void setUniformInt(String name, int value){
		glUniform1i(glGetUniformLocation(programID, name), value);
	}

	public void setUniformInt2(String name, int x, int y){
		glUniform2i(glGetUniformLocation(programID, name), x, y);
	}

	public void setUniformInt3(String name, int x, int y, int z){
		glUniform3i(glGetUniformLocation(programID, name), x, y, z);
	}

	public void setUniformInt4(String name, int x, int y, int z, int w){
		glUniform4i(glGetUniformLocation(programID, name), x, y, z, w);
	}

	public void setUniformFloat(String name, float value){
		glUniform1f(glGetUniformLocation(programID, name), value);
	}

	public void setUniformFloat2(String name, float x, float y){
		glUniform2f(glGetUniformLocation(programID, name), x, y);
	}

	public void setUniformFloat3(String name, float x, float y, float z){
		glUniform3f(glGetUniformLocation(programID, name), x, y, z);
	}

	public void setUniformFloat4(String name, float x, float y, float z, float w){
		glUniform4f(glGetUniformLocation(programID, name), x, y, z, w);
	}

	public void setUniformBoolean(String name, boolean value){
		glUniform1i(glGetUniformLocation(programID, name), value ? 1 : 0);
	}

	public void setUniformVec2(String name, Vector2f value){
		glUniform2f(glGetUniformLocation(programID, name), value.x, value.y);
	}

	public void setUniformVec3(String name, Vector3f value){
		glUniform3f(glGetUniformLocation(programID, name), value.x, value.y, value.z);
	}

	public void setUniformVec4(String name, Vector4f value){
		glUniform4f(glGetUniformLocation(programID, name), value.x, value.y, value.z, value.w);
	}

	public void destroy() {
		glDetachShader(programID, vertexID);
		glDetachShader(programID, fragmentID);
		glDeleteProgram(programID);
	}
}