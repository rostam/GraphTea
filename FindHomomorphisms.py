import more_itertools as miter
import itertools as iter
import networkx as nx
import string
# import matplotlib.pyplot as plt

G = nx.read_graph6('G.g6')
H = nx.read_graph6('H.g6')
# G = nx.petersen_graph()
# H = nx.complete_graph(3)

G_nodes = G.nodes()
H_nodes = H.nodes()

iterable = string.ascii_lowercase[0:len(G_nodes)]
is_homomorphism_found = False
for part in miter.set_partitions(iterable, len(H_nodes)):
    is_correct_homomorphism = True
    if is_homomorphism_found:
        break
    for p in part:
        if not is_correct_homomorphism:
            break
        if len(p) == 1:
            continue
        all_combs = iter.combinations(p, 2)
        for a_comb in all_combs:
            v1 = ord(a_comb[0]) - 97
            v2 = ord(a_comb[1]) - 97
            if G.has_edge(v1, v2):
                is_correct_homomorphism = False
                break

    if is_correct_homomorphism:
        all_combs = iter.combinations(range(0,len(part)), 2)
        for a_comb in all_combs:
            if is_correct_homomorphism:
                v1H = a_comb[0]
                v2H = a_comb[1]
                if not H.has_edge(v1H, v1H):
                    for p1 in part[a_comb[0]]:
                        if is_correct_homomorphism:
                            for p2 in part[a_comb[1]]:
                                v1G = ord(p1) - 97
                                v2G = ord(p2) - 97
                                if G.has_edge(v1G,v1G):
                                    is_correct_homomorphism = False
                                    break

    if is_correct_homomorphism:
        is_homomorphism_found = True
        with open("homomorphism_G_H",'w') as f:
            for p in part:
                for a in p:
                    f.write(a)
                f.write(' ')
            f.flush()

        # print([''.join(p) for p in part])
        break

if not is_homomorphism_found:
    with open("homomorphism_G_H",'w') as f:
        f.write("not found")
        f.flush()
        f.close()


# nx.draw(G, with_labels=True)
# plt.draw()
# plt.show()
