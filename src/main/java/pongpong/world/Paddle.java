package pongpong.world;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;
import pongpong.util.Rectangle;
import simbad.sim.BlockWorldObject;

import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingBox;
import javax.media.j3d.Material;
import javax.media.j3d.Transform3D;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

public class Paddle extends BlockWorldObject {

	public static final float MIN_Z = -9f;

	public static final float MAX_Z = 9f;

	private Vector3d startPosition;

	private float baseSpeed = .1f;

	private float maxSpeed = 1f;

	private float jolt = .001f;

	private float sx, sy, sz;

	private float acceleration;

	private Color3f color;

	Paddle(Vector3d pos, Vector3f extent) {
		this(pos, extent, null);
	}

	public Paddle(Vector3d pos, Vector3f extent, Color3f color) {
		this.sx = extent.x;
		this.sy = extent.y;
		this.sz = extent.z;
		pos.y += sy/2;

		this.color = color != null ? color : new Color3f(1, 1, 1);
		this.compilable = true;
		this.acceleration = 0;

		this.startPosition = pos;

		this.create3D();
		this.translateTo(pos);
	}

	public float getLength() {
		return this.sz;
	}

	public Rectangle get2DRepresentation() {
		Vector3f pos = this.getTranslation();
		return new Rectangle(pos.x - sx, pos.z - sz, sx * 2, sz * 2);
	}

	public float getAcceleration() {
		return this.acceleration;
	}

	public void setBaseSpeed(float baseSpeed) {
		this.baseSpeed = baseSpeed;
	}

	public void setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public void setJolt(float jolt) {
		this.jolt = jolt;
	}

	public void up() {
		// Not allowed to get out by the top
		if (this.getTranslation().getZ() - this.sz / 2 < MIN_Z) {
			this.resetAcceleration();
			return;
		}

		if (this.acceleration == 0)
			this.acceleration = baseSpeed;

		this.translateTo(new Vector3d(0, 0, -acceleration));
		this.acceleration = Math.min(this.acceleration + jolt, maxSpeed);

		this.computeTransformedBounds();
	}

	public void down() {
		// Not allowed to get out by the bottom
		if (this.getTranslation().getZ() + this.sz / 2 > MAX_Z) {
			this.resetAcceleration();
			return;
		}

		if (this.acceleration == 0)
			this.acceleration = baseSpeed;

		this.translateTo(new Vector3d(0, 0, acceleration));
		this.acceleration = Math.min(this.acceleration + jolt, maxSpeed);

		this.computeTransformedBounds();
	}

	public void moveToStartPosition() {
		Vector3f pos = this.getTranslation();
		Vector3d diffVec = new Vector3d(
				this.startPosition.x - pos.x,
				this.startPosition.y - pos.y,
				this.startPosition.z - pos.z
		);

		this.resetAcceleration();

		this.translateTo(diffVec);
		this.computeTransformedBounds();
	}

	public void resetAcceleration() {
		this.acceleration = 0;
	}

	private void create3D() {
		super.create3D(true);

		localToVworld = new Transform3D();

		appearance = new Appearance();
		Material mat = new Material();
		mat.setDiffuseColor(this.color);
		mat.setSpecularColor(new Color3f(0, 0, 0));
		appearance.setMaterial(mat);

		int flags = Primitive.GEOMETRY_NOT_SHARED | Primitive.ENABLE_GEOMETRY_PICKING | Primitive.GENERATE_NORMALS;
		flags |= Primitive.ENABLE_APPEARANCE_MODIFY;

		Box box = new Box(sx/2,sy/2,sz/2,flags,appearance,0);
		box.setPickable(true);
		box.setCollidable(true);

		BoundingBox bounds = new BoundingBox();
		bounds.setUpper( sx / 2, sy / 2, sz / 2);
		bounds.setLower(-sx / 2,-sy / 2,-sz / 2);
		setBounds(bounds);

		setColor(color);
		addChild(box);
	}

}
