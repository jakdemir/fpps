package patmat

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import patmat.Huffman._

@RunWith(classOf[JUnitRunner])
class HuffmanSuite extends FunSuite {

  trait TestTrees {
    val t1 = Fork(Leaf('a', 2), Leaf('b', 3), List('a', 'b'), 5)
    val t2 = Fork(Fork(Leaf('a', 2), Leaf('b', 3), List('a', 'b'), 5), Leaf('d', 4), List('a', 'b', 'd'), 9)
    val t3 = Fork(Leaf('d', 4), Fork(Leaf('a', 2), Leaf('b', 3), List('a', 'b'), 5), List('d', 'a', 'b'), 9)
  }


  test("weight of a t1 tree") {
    new TestTrees {
      assert(weight(t1) === 5)
    }
  }

  test("weight of a t2 tree") {
    new TestTrees {
      assert(weight(t2) === 9)
    }
  }

  test("chars of a t1 tree") {
    new TestTrees {
      assert(chars(t1) === List('a', 'b'))
    }
  }


  test("chars of a t2 tree") {
    new TestTrees {
      assert(chars(t2) === List('a', 'b', 'd'))
    }
  }

  test("string2chars(\"hello, world\")") {
    assert(string2Chars("hello, world") === List('h', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd'))
  }

  test("times of a hellowol") {
    new TestTrees {
      assert(times(string2Chars("hellowol")) === List[(Char, Int)](('h', 1), ('e', 1), ('l', 3), ('o', 2), ('w', 1)))
    }
  }


  test("Singleton test true") {
    new TestTrees {
      assert(singleton(List[CodeTree](t1, t1)) == true)
    }
  }

  test("Singleton test false") {
    new TestTrees {
      assert(singleton(List[CodeTree](t1, t2)) == false)
    }
  }

  test("Singleton test t2") {
    new TestTrees {
      assert(singleton(List[CodeTree](t2, t2, t2)) == true)
    }
  }

  test("Singleton test t2 single") {
    new TestTrees {
      assert(singleton(List[CodeTree](t2)) == true)
    }
  }

  test("makeOrderedLeafList for some frequency table") {
    assert(makeOrderedLeafList(List(('t', 2), ('e', 1), ('x', 3))) === List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 3)))
  }

  test("combine of some leaf list") {
    val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
    assert(combine(leaflist) === List(Fork(Leaf('e', 1), Leaf('t', 2), List('e', 't'), 3), Leaf('x', 4)))
  }


  test("Until testing") {
    new TestTrees {
      assert(createCodeTree(string2Chars("aabbbdddd")) === t3)
    }
  }

  test("decode testing") {
    new TestTrees {
      assert(decodedSecret === string2Chars("huffmanestcool"))
    }
  }

  test("encode testing") {
    new TestTrees {
      assert(encode(frenchCode)(string2Chars("huffmanestcool")) === List(0, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 1, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1)
      )
    }
  }

  test("decode and encode a very short text should be identity") {
    new TestTrees {
      assert(decode(t1, encode(t1)("ab".toList)) === "ab".toList)
    }
  }

  test("Quick Encode") {
    new TestTrees {
      assert(encode(frenchCode)(string2Chars("huffmanestcool")).toString() == quickEncode(frenchCode)(string2Chars("huffmanestcool")).toString())
    }
  }

}
