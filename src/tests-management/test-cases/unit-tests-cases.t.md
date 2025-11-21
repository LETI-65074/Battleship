# Test Suite: Unit tests cases

TMS-ID: TS-UTC-001
Tags: #unit-tests #test-cases

* TC-Position-001: PositionTest – testar getRow, getColumn, equals, hashCode, isAdjacentTo, occupy/shoot, isOccupied, isHit e toString
* TC-Compass-001: CompassTest – testar os valores do enum Compass e qualquer comportamento associado
* TC-Ship-001: ShipTest – testar buildShip para todas as categorias, stillFloating, extremidades (top/bottom/left/right), occupies, tooCloseTo, shoot e toString

* TC-Barge-001: BargeTest – testar tamanho, categoria, ocupação em todas as orientações e afundamento da barca
* TC-Caravel-001: CaravelTest – testar tamanho 2, ocupação em todas as orientações, afundamento e exceções para bearing inválido (UNKNOWN)
* TC-Carrack-001: CarrackTest – testar tamanho 3, ocupação em todas as orientações, afundamento e exceções
* TC-Frigate-001: FrigateTest – testar tamanho 4, ocupação em todas as orientações, afundamento e exceções
* TC-Galleon-001: GalleonTest – testar tamanho 5, geometria completa (NORTH/SOUTH/EAST/WEST), afundamento, tooCloseTo(IPosition) e tooCloseTo(IShip)

* TC-Fleet-001: FleetTest – testar addShip (válidos e inválidos), getShips, getShipsLike, getFloatingShips, shipAt e métodos de impressão (printStatus, printShipsByCategory, printFloatingShips, printAllShips)
* TC-Game-001: GameTest – testar fire (tiros válidos, inválidos e repetidos), contadores de invalidShots, repeatedShots, hits, sunkShips, getRemainingShips e métodos de impressão do tabuleiro (printBoard, printValidShots, printFleet)
* TC-App-001: AppTest – testar o main com input simulado (tiro que afunda, tiro que falha e saída com 'q')

