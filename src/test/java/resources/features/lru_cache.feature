Feature: cache is a data structure which stores values in a quick access storage like memory.
  The cache can hold certain no. of keys and when it reaches max capacity,
  if an eviction policy is specified, the cache will try to make some space
  by evicting one or more keys as per eviction policy.

  Scenario:
    Given an LRU cache with size 2
    When an item is added to the cache with
      |  2   |  2    |
    Then the cache should contain the item
      |  2   |  2    |

  Scenario:
    Given an LRU cache with size 2
    When an item is added to the cache with
      |  2   |  2    |
      |  1   |  1    |
    Then the cache should contain the item
      |  2   |  2    |
      |  1   |  1    |

  Scenario:
    Given an LRU cache with size 2
    When an item is added to the cache with
      |  2   |  2    |
      |  1   |  1    |
      |  3   |  3    |
    Then the cache should contain the item
      |  1   |  1    |
      |  3   |  3    |

  Scenario:
    Given an LRU cache with size 2
    When an item is added to the cache with
      |  2   |  2    |
      |  1   |  1    |
    And get an from cache with key 2
    Then the cache should contain the item
      |  1   |  1    |
      |  2   |  2    |

  Scenario:
    Given an LRU cache with size 3
    When an item is added to the cache with
      |  2   |  2    |
      |  1   |  1    |
      |  3   |  3    |
    And get an from cache with key 1
    Then the cache should contain the item
      |  2   |  2    |
      |  3   |  3    |
      |  1   |  1    |

  Scenario:
    Given an LRU cache with size 3
    When an item is added to the cache with
      |  2   |  2    |
      |  1   |  1    |
      |  3   |  3    |
    And get an from cache with key 1
    And update the cache value to
      |  2   |  4    |
    Then the cache should contain the item
      |  3   |  3    |
      |  1   |  1    |
      |  2   |  4    |

  Scenario:
    Given an LRU cache with size 3
    When an item is added to the cache with
      |  2   |  2    |
      |  1   |  1    |
      |  3   |  3    |
    And the cache is busted
    Then the cache should not contain any items
      |  3   |  3    |
      |  1   |  1    |
      |  2   |  4    |