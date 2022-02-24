fun main() {

    val (n,m) = readLine()!!.split(" ").map{it.toInt()}
    val nodesEff = readLine()!!.split(" ").map{it.toInt()}
    val fathers = readLine()!!.split(" ").map{it.toInt()}

    val baseNode = Node(index = 0,children = mutableListOf(), money = m,effectivity = 1,parent = -1)
    val assistedTree = mutableListOf(baseNode)

    //add all nodes
    for (i in 0 until n){
        val node = Node(index = i+1,children = mutableListOf(), money = 0,effectivity = nodesEff[i],parent = fathers[i])
        assistedTree.add(node)
    }
    //add children to their parents
    for (i in 1 until assistedTree.size){
        val node = assistedTree[i]
        assistedTree[node.parent].children.add(node)
    }
    //effectivity (2,4) -> effectivity(1,2)
    //apply gcd to every node
    for (i in assistedTree.indices){
        assistedTree[i].normalize()
    }
    splitMoney(baseNode)

    for (i in 1 until assistedTree.size){
//        ${findNode(baseNode,i).effectivity}
        print("${assistedTree[i].money} ")
    }


}
data class Node(val index : Int, val children : MutableList<Node>,var money : Int, var effectivity : Int,val parent : Int){
    val nodeChildrenEffectivity by lazy{children.sumOf { it.effectivity }}
    val gcd by lazy{
        if(children.isEmpty()){
            1
        }else{
            var gcd = children[0].effectivity
            children.onEach { gcd = gcd(gcd,it.effectivity) }
            gcd
        }
    }

}
fun Node.normalize(){
    children.forEach { it.effectivity /= gcd }
}

fun splitMoney(node : Node){
    for (el in node.children){
        el.money = node.money /node.nodeChildrenEffectivity * el.effectivity
//        print(${el.index})
//        println("${el.index} ${el.money} ${node.nodeChildrenEffectivity} ${el.effectivity}")
//        println(node)
        splitMoney(el)
    }
}
fun gcd (n1:Int,n2:Int):Int{
    var num1 = n1
    var num2 = n2
    while (num1 != num2) ( if (num1> num2) num1 -= num2 else num2 -= num1 )
    return when(num1){
        0->num2
        else -> num1
    }
}