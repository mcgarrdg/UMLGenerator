package Core.SD;

import Core.TypeData;

import java.util.ArrayList;

public class SDGraphMethodData implements SDGraphItem{

	private String classCalledFrom;
	private String methodName;
	private ArrayList<TypeData> argData;
	private String classCalledOn;
	private String returnName;
	
	public SDGraphMethodData(String calledFrom, String method, String calledOn, String retrnName, ArrayList<TypeData> args) {
		this.classCalledFrom = 	calledFrom;
		this.methodName = method;
		this.classCalledOn = calledOn;
		this.returnName = retrnName;
		this.argData = args;
	}
	
	public SDGraphMethodData()
	{
		this.classCalledFrom = 	null;
		this.methodName = null;
		this.classCalledOn = null;
		this.returnName = null;
		this.argData = new ArrayList<TypeData>();
	}
	
	@Override
	public String toSDEditString() {
		StringBuilder builder = new StringBuilder();
		String modifiedName = this.methodName;
		if(this.methodName.equals("init"))
		{
			modifiedName = "new";
		}
		if(!this.returnName.equals("void"))
		{
			builder.append(this.classCalledFrom).append(":").append(this.returnName).append("=").append(this.classCalledOn).append(".").append(modifiedName).append("(");
		}
		else
		{
			builder.append(this.classCalledFrom).append(":").append(this.classCalledOn).append(".").append(modifiedName).append("(");
		}
		for (TypeData arg : this.argData) {
			builder.append(arg.getExtendedName());
			builder.append(",");
		}
		if (builder.charAt(builder.length() - 1) == ',')
		{
			builder.deleteCharAt(builder.length() - 1);
		}
		builder.append(")\n");
		return builder.toString();
	}

	public String getClassCalledFrom() {
		return classCalledFrom;
	}

	public void setClassCalledFrom(String classCalledFrom) {
		this.classCalledFrom = classCalledFrom;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodSignature) {
		this.methodName = methodSignature;
	}

	public String getClassCalledOn() {
		return classCalledOn;
	}

	public void setClassCalledOn(String classCalledOn) {
		this.classCalledOn = classCalledOn;
	}

	public String getReturnName() {
		return returnName;
	}

	public void setReturnName(String returnName) {
		this.returnName = returnName;
	}

	public void setArgumentData(ArrayList<TypeData> args)
	{
		this.argData = args;
	}
}
