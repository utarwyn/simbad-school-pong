package pongpong.world;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;
import simbad.sim.BlockWorldObject;

import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingBox;
import javax.media.j3d.Material;
import javax.media.j3d.Transform3D;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

public class Paddle extends BlockWorldObject {

	private float sx, sy, sz;

	private Color3f color;

	Paddle(Vector3d pos, Vector3f extent) {
		this(pos, extent, null);
	}

	Paddle(Vector3d pos, Vector3f extent, Color3f color) {
		this.sx = extent.x;
		this.sy = extent.y;
		this.sz = extent.z;
		pos.y += sy/2;

		this.color = color != null ? color : new Color3f(1, 1, 1);
		this.compilable = true;

		this.create3D();
		this.translateTo(pos);
	}

	public void up() {
		this.translateTo(new Vector3d(0, 0, -.1f));
		this.computeTransformedBounds();
	}

	public void down() {
		this.translateTo(new Vector3d(0, 0, .1f));
		this.computeTransformedBounds();
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
