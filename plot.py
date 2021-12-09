from mpl_toolkits.mplot3d import Axes3D
import matplotlib.pyplot as plt


def getName(i):
    a = str(i)
    while len(a)<5:
        a="0"+a
    return a+".png"
    
#coordinates
zmin=0
zmax=0
ymin=0
ymax=0
xmax=0
xmin=0
with open("out.txt") as f:
    i=0
    while True:
        fig = plt.figure()
        ax = fig.add_subplot(111, projection='3d')
        xs=[]
        ys=[]
        zs=[]
        xr=[]
        yr=[]
        zr=[]

        s=f.readline()
        r=f.readline()
        for line in s.split(";"):
            try:
                val=line.split(",")
                xs.append(int(val[0]))
                ys.append(int(val[1]))
                zs.append(int(val[2]))
            except:
                pass
        if i == 0:
            xmax=max(xs)
            xmin=min(xs)
            ymax=max(ys)
            ymin=min(ys)
            zmax=max(zs)
            zmin=min(zs)
        for line in r.split(";"):
            try:
                val=line.split(",")
                xr.append(int(val[0]))
                yr.append(int(val[1]))
                zr.append(int(val[2]))
            except:
                pass
       #     print("value:",val)
        # For each set of style and range settings, plot n random points in the box
        # defined by x in [23, 32], y in [0, 100], z in [zlow, zhigh].
        ax.scatter(xs, ys, zs, c="b", marker="o")
        ax.scatter(xr, yr, zr, c="r", marker="o")
        ax.set_xlim3d(xmin,xmax)
        ax.set_ylim3d(ymin,ymax)
        ax.set_zlim3d(zmin,zmax)
        ax.set_xlabel('X ')
        ax.set_ylabel('Y ')
        ax.set_zlabel('Z ')
        ax.text2D(0.05, 0.95, "Particles left:"+str(len(xs)), transform=ax.transAxes)

        plt.savefig(fname="pyt/a"+getName(i),dpi=400)
        i+=1

