package maths;

public class Matrix4f {
    private static final int SIZE = 4 * 4;
    private float[] matrix = new float[SIZE];

    public Matrix4f() {
    }

    public static Matrix4f identity(){
        Matrix4f returnMatrix = new Matrix4f();
        returnMatrix.matrix[0 + 0 * 4] = 1.0f;
        returnMatrix.matrix[1 + 1 * 4] = 1.0f;
        returnMatrix.matrix[2 + 2 * 4] = 1.0f;
        returnMatrix.matrix[3 + 3 * 4] = 1.0f;

        return returnMatrix;
    }

    public static Matrix4f translate(Vector3f vector){
        Matrix4f returnMatrix = identity();
        returnMatrix.matrix[0 + 3 * 4] = vector.getX();
        returnMatrix.matrix[1 + 3 * 4] = vector.getY();
        returnMatrix.matrix[2 + 3 * 4] = vector.getZ();

        return returnMatrix;
    }

    public static Matrix4f rotate(float angle){
        Matrix4f returnMatrix = identity();
        float r = (float) Math.toRadians(angle);
        float cos = (float) Math.cos(r);
        float sin = (float) Math.sin(r);

        returnMatrix.matrix[0 + 0 * 4] = cos;
        returnMatrix.matrix[1 + 0 * 4] = cos;
        returnMatrix.matrix[0 + 1 * 4] = cos;
        returnMatrix.matrix[1 + 1 * 4] = cos;

        return returnMatrix;
    }

    public Matrix4f multiply(Matrix4f mat4f){
        Matrix4f result = new Matrix4f();
        for (int x = 0; x < 4; x++){
            for (int y = 0; y < 4; y++){
                float sum = 0.0f;
                for (int e = 0; e < 4; e++){
                    sum += this.matrix[x + e * 4];
                }
                result.matrix[x + y * 4] = sum;
            }
        }
        return result;
    }
}
